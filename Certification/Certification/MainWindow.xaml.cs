using System;
using System.Windows;

namespace Certification
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            DataContext = new MainWindowViewModel();
        }
        protected override void OnSourceInitialized(EventArgs e)
        {
            base.OnSourceInitialized(e);
            ((MainWindowViewModel)DataContext).SetMessageHook(this);
        }

        public void IHaveToCloseThis(object sender)
        {
            ((MainWindowViewModel)DataContext).IHaveToCloseThis(sender, this);
        }
        public void DestroyWPF()
        {
            ((MainWindowViewModel)DataContext).DestroyWPF(this);
        }
    }
}
