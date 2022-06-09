using Certification.Base;
using Certification.Data;
using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Interop;
using System.Windows.Media;
using System.Windows.Threading;

namespace Certification
{
    internal class MainWindowViewModel : INotifyPropertyChanged
    {
        private string _retText;
        private bool _startEnable;

        private string _jksURL;
        private string _jksFile;
        private int _jksValidTime;
        private string _jksPW;
        private string _jksName;
        private string _jksOrganizationalUnit;
        private string _jksOrganizationalName;
        private string _jksCity;
        private string _jksState;
        private string _jksCountry;

        DispatcherTimer _timer;
        public MainWindowViewModel()
        {
            _retText = "";
            _startEnable = true;

            _jksURL = "";
            _jksFile = "";
            _jksValidTime = 3650;
            _jksPW = "";
            _jksName = "";
            _jksOrganizationalUnit = "";
            _jksOrganizationalName = "";
            _jksCity = "seoul";
            _jksState = "seoul";
            _jksCountry = "kr";
            _timer = new();
            _timer.Interval = TimeSpan.FromMilliseconds(10);
            _timer.Tick += _timer_Tick;
            _timer.IsEnabled = true;
            StartCmd = new CommandImpl(StartEvent);
            SetCmd = new CommandImpl(SetEvent);
            CloseCmd = new CommandImpl(CloseEvent);
            _timer.Start();
        }

        public string RetText
        {
            get => _retText;
            set
            {
                _retText = value;
                OnPropertyChanged();
            }
        }
        public bool StartEnable
        {
            get => _startEnable;
            set
            {
                _startEnable = value;
                OnPropertyChanged();
            }
        }


        public string JksURL
        {
            get => _jksURL;
            set
            {
                _jksURL = value;
                OnPropertyChanged();
            }
        }
        public string JksFile
        {
            get => _jksFile;
            set
            {
                _jksFile = value;
                OnPropertyChanged();
            }
        }
        public int JksValidTime
        {
            get => _jksValidTime;
            set
            {
                _jksValidTime = value;
                OnPropertyChanged();
            }
        }
        public string JksPW
        {
            get => _jksPW;
            set
            {
                _jksPW = value;
                OnPropertyChanged();
            }
        }
        public string JksName
        {
            get => _jksName;
            set
            {
                _jksName = value;
                OnPropertyChanged();
            }
        }
        public string JksOrganizationalUnit
        {
            get => _jksOrganizationalUnit;
            set
            {
                _jksOrganizationalUnit = value;
                OnPropertyChanged();
            }
        }
        public string JksOrganizationalName
        {
            get => _jksOrganizationalName;
            set
            {
                _jksOrganizationalName = value;
                OnPropertyChanged();
            }
        }
        public string JksCity
        {
            get => _jksCity;
            set
            {
                _jksCity = value;
                OnPropertyChanged();
            }
        }
        public string JksState
        {
            get => _jksState;
            set
            {
                _jksState = value;
                OnPropertyChanged();
            }
        }
        public string JksCountry
        {
            get => _jksCountry;
            set
            {
                _jksCountry = value;
                OnPropertyChanged();
            }
        }
        public ICommand StartCmd { get; }
        public ICommand SetCmd { get; }
        public ICommand CloseCmd { get; }

        private void _timer_Tick(object? sender, EventArgs e)
        {
            string? data = FBaseFunc.Ins.DeRet();
            if (data != null)
            {
                RetText = $"{RetText}\n{data}";
            }

            if (StartEnable == false && FBaseFunc.Ins.IsAliveThread(BaseLib_Net6.FThread.eTHREAD.TH1) == false)
            {
                StartEnable = true;
            }
        }
        private void StartEvent(object? obj)
        {
            FBaseFunc.Ins.RunCmd();
            StartEnable = false;
        }
        private void SetEvent(object? obj)
        {
            FBaseFunc.Ins.SetData(JksURL, JksFile, JksValidTime, JksPW, JksName, JksOrganizationalUnit, JksOrganizationalName, JksCity, JksState, JksCountry);
        }
        private void CloseEvent(object? obj)
        {
            if (MessageBox.Show("are you sure?", "close", MessageBoxButton.YesNo) != MessageBoxResult.Yes)
            {
                MessageBox.Show("no click");
                return;
            }

            FBaseFunc.Ins.Terminate();

            if (obj is Window window)
            {
                App.WindowIns.DestroyWPF();
                window.Close();
            }
        }
        public void SetMessageHook(Window mother)
        {
            WindowInteropHelper helper = new(mother);
            HwndSource source = HwndSource.FromHwnd(helper.Handle);
            source.AddHook(HookingFunc);
        }
        public IntPtr HookingFunc(IntPtr hwnd, int msg, IntPtr wParam, IntPtr lParam, ref bool handled)
        {
            if (msg == 0x10)
            {
                handled = true;
            }
            return IntPtr.Zero;
        }
        public void IHaveToCloseThis(object sender, MainWindow mother)
        {
            MainWindow? parent = FindParent<MainWindow>((Button)sender);
            if (parent == null)
            {
                parent = mother;
            }
            HwndSource hwndSource = (HwndSource)PresentationSource.FromVisual(parent as Visual);
            if (hwndSource != null)
            {
                hwndSource.RemoveHook(new HwndSourceHook(HookingFunc));
            }
            Window.GetWindow(mother).Close();
        }
        private static T? FindParent<T>(DependencyObject dependencyObject) where T : DependencyObject
        {
            var parent = VisualTreeHelper.GetParent(dependencyObject);
            if (parent == null)
            {
                return null;
            }

            var parentT = parent as T;
            return parentT ?? FindParent<T>(parent);
        }
        public void DestroyWPF(Visual mother)
        {
            HwndSource hwndSource = (HwndSource)PresentationSource.FromVisual(mother);
            if (hwndSource != null)
            {
                hwndSource.RemoveHook(new HwndSourceHook(HookingFunc));
            }
        }

        public event PropertyChangedEventHandler? PropertyChanged;
        protected virtual void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}