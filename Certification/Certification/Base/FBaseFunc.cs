using System.Diagnostics;
using System.IO;
using System.Threading;
using System.Windows;

namespace Certification.Base
{
    internal partial class FBaseFunc : BaseLib_Net6.FThread
    {
        public FBaseFunc()
        {
            _retText = new();
            _process = new();
            _cmd = new();
            _jksUrl = "";
            _jksFile = "";
            _jksValidTime = 3650;
            _jksPW = "";
            _jksName = "";
            _jksOrganizationalUnit = "";
            _jksOrganizationalName = "";

            SetThreadInterval(eTHREAD.TH1, 10);
        }

        private static bool CheckFolder(string path)
        {
            string temp = path;
            DirectoryInfo filePath = new(path);
            DirectoryInfo di = new(temp.Replace(filePath.Name, ""));

            if (di.Exists == false)
            {
                di.Create();
                return false;
            }

            return true;
        }
        private static bool CheckFile(string path, bool createFile = false)
        {
            if (File.Exists(path) == false)
            {
                if (createFile)
                {
                    using (File.Create(path))
                    {

                    }
                }

                return false;
            }

            return true;
        }
        private static bool IsFileLocked(FileInfo file)
        {
            FileStream? stream = null;
            try
            {
                stream = file.Open(FileMode.Open, FileAccess.ReadWrite, FileShare.None);
            }
            catch (IOException)
            {
                return true;
            }
            finally
            {
                stream?.Close();
            }

            return false;
        }
        private void AddRet(string? data)
        {
            if (data == null)
            {
                return;
            }

            _retText.Enqueue(data);
        }
        private void SendCmd(string msg)
        {
            long timeout = 0;
            while (!_sendEnable)
            {
                Thread.Sleep(1000);
                timeout++;
                if (timeout > 5)
                {
                    break;
                }
            }

            AddRet(msg);
            _process.StandardInput.WriteLine(msg);
            _sendEnable = false;
        }
        private void Cmd_ErrorDataReceived(object sender, DataReceivedEventArgs e)
        {
            _sendEnable = true;
            AddRet(e.Data);
        }
        private void Cmd_OutputDataReceived(object sender, DataReceivedEventArgs e)
        {
            _sendEnable = true;
            AddRet(e.Data);
        }
        public override bool ProcThread1()
        {
            if (!ValidData())
            {
                return false;
            }

            CheckFolder($"./{_jksFile}/{_jksFile}.jks");
            if (CheckFile($"./{_jksFile}/{_jksFile}.jks") || CheckFile($"./{_jksFile}/{_jksFile}.cer"))
            {
                if (MessageBox.Show("이미 동일한 이름의 인증서가 존재합니다.\n그래도 수행하시겠습니까?", "경고", MessageBoxButton.YesNo) != MessageBoxResult.Yes)
                {
                    AddRet("[취소]\tAlready Exist JKS");
                    return false;
                }

                if (CheckFile($"./{_jksFile}/{_jksFile}.jks"))
                {
                    if (IsFileLocked(new FileInfo($"./{_jksFile}/{_jksFile}.jks")))
                    {
                        Thread.Sleep(1000);
                    }
                }
                if (CheckFile($"./{_jksFile}/{_jksFile}.cer"))
                {
                    if (IsFileLocked(new FileInfo($"./{_jksFile}/{_jksFile}.cer")))
                    {
                        Thread.Sleep(1000);
                    }
                }
            }

            string msg = $"keytool -genkey -alias {_jksUrl} -keyalg RSA -keystore ./{_jksFile}/{_jksFile}.jks -validity {_jksValidTime}";
            SendCmd(msg);

            msg = _jksPW;
            SendCmd(msg);

            msg = _jksPW;
            SendCmd(msg);

            msg = _jksName;
            SendCmd(msg);

            msg = _jksOrganizationalUnit;
            SendCmd(msg);

            msg = _jksOrganizationalName;
            SendCmd(msg);

            msg = _jksCity;
            SendCmd(msg);

            msg = _jksState;
            SendCmd(msg);

            msg = _jksCountry;
            SendCmd(msg);

            msg = "y";
            SendCmd(msg);

            msg = $"keytool -export -alias {_jksUrl} -keystore ./{_jksFile}/{_jksFile}.jks -rfc -file ./{_jksFile}/{_jksFile}.cer";
            SendCmd(msg);

            msg = _jksPW;
            SendCmd(msg);

            msg = $"keytool -import -alias {_jksUrl}Trust -file ./{_jksFile}/{_jksFile}.cer -keystore ./{_jksFile}/{_jksFile}.jks";
            SendCmd(msg);

            msg = _jksPW;
            SendCmd(msg);

            msg = "y";
            SendCmd(msg);

            msg = $"server:\n  ssl:\n    enabled: true\n    key-alias: {_jksUrl}\n    key-store: {_jksFile}.jks\n    key-store-password: '{_jksPW}'\n    key-password: '{_jksPW}'\n    trust-store: {_jksFile}.jks\n    trust-store-password: '{_jksPW}'\n";
            AddRet(msg);
            if (CheckFile($"./{_jksFile}/application.yml", true))
            {
                if (IsFileLocked(new FileInfo($"./{_jksFile}/application.yml")))
                {
                    Thread.Sleep(1000);
                }

                File.Delete($"./{_jksFile}/application.yml");
                AddRet("delete application.yml");
            }

            File.WriteAllText($"./{_jksFile}/application.yml", msg);
            AddRet("write application.yml");

            Process.Start(System.Environment.GetEnvironmentVariable("WINDIR") + @"\explorer.exe", $"{System.Environment.CurrentDirectory}\\{_jksFile}");
            return false;
        }
        private void InitCmd()
        {
            _cmd.FileName = @"cmd.exe";
            _cmd.WindowStyle = ProcessWindowStyle.Hidden;
            _cmd.CreateNoWindow = true;
            _cmd.UseShellExecute = false;
            _cmd.RedirectStandardOutput = true;
            _cmd.RedirectStandardInput = true;
            _cmd.RedirectStandardError = true;
            _process.OutputDataReceived += Cmd_OutputDataReceived;
            _process.ErrorDataReceived += Cmd_ErrorDataReceived;
            _process.StartInfo = _cmd;
            _process.Start();
            _process.BeginOutputReadLine();
            _process.BeginErrorReadLine();
        }

        public void Init()
        {
            InitCmd();
        }
        public void Terminate()
        {
            _sendEnable = true;
            CloseThread();
            try
            {
                _process.StandardInput.Close();
                _process.CancelOutputRead();
                _process.CancelErrorRead();
                _process.WaitForExit();
                _process.Close();
            }
            catch
            {

            }
        }

        private bool ValidData()
        {
            if (_jksUrl.Length <= 0)
            {
                AddRet("[실패]\tURL 이름이 없음.");
                return false;
            }

            if (_jksFile.Length <= 0)
            {
                AddRet("[실패]\t파일 이름이 없음.");
                return false;
            }

            if (_jksPW.Length <= 5)
            {
                AddRet("[실패]\t비밀번호가 6자 이상이어야 함.");
                return false;
            }

            if (_jksName.Length <= 0)
            {
                AddRet("[실패]\t이름이 없음.");
                return false;
            }

            if (_jksOrganizationalUnit.Length <= 0)
            {
                AddRet("[실패]\t파일 이름이 없음.");
                return false;
            }

            if (_jksOrganizationalName.Length <= 0)
            {
                AddRet("[실패]\t파일 이름이 없음.");
                return false;
            }

            return true;
        }
        public bool RunCmd()
        {
            CreateThread(eTHREAD.TH1);

            return true;
        }
        public void SetData(string jksUrl, string jksFile, int jksValidTime, string jksPW, string jksName, string jksOrganizationalUnit, string jksOrganizationalName, string jksCity = "seoul", string jksState = "seoul", string jksCountry = "kr")
        {
            _jksUrl = jksUrl;
            _jksFile = jksFile;
            _jksValidTime = jksValidTime;
            _jksPW = jksPW;
            _jksName = jksName;
            _jksOrganizationalUnit = jksOrganizationalUnit;
            _jksOrganizationalName = jksOrganizationalName;
            _jksCity = jksCity;
            _jksState = jksState;
            _jksCountry = jksCountry;

            AddRet("Set Data");
        }
        public string? DeRet()
        {
            if (_retText.Count > 0)
            {
                return _retText.Dequeue();
            }

            return null;
        }
    }
}
