using System.Collections.Generic;
using System.Diagnostics;

namespace Certification.Base
{
    internal partial class FBaseFunc
    {
        private const string KEY_TOOL_PATH = "./keytool.exe";

        private static FBaseFunc? _ins;
        public static FBaseFunc Ins
        {
            get => _ins ??= new();
        }

        private bool _sendEnable = false;
        private Queue<string> _retText;
        private readonly Process _process;
        private readonly ProcessStartInfo _cmd;

        private string _jksUrl;
        private string _jksFile;
        private int _jksValidTime;
        private string _jksPW;
        private string _jksName;
        private string _jksOrganizationalUnit;
        private string _jksOrganizationalName;
        private string _jksCity = "seoul";
        private string _jksState = "seoul";
        private string _jksCountry = "kr";
    }
}
