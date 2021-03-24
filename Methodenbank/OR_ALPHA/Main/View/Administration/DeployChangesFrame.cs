using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Windows.Forms;

namespace OrAlpha.Main.View.Administration
{
    public partial class DeployChangesFrame : Form
    {
        public DeployChangesFrame()
        {
            InitializeComponent();
        }

        private void OnCancelButtonClick(object sender, EventArgs e)
        {
            this.Close();
        }

        private void OnDeployChangesButtonClick(object sender, EventArgs e)
        {
            this.Enabled = false;
            ThreadPool.SetMaxThreads(4,4);
            DeployChanges();
            ThreadPool.QueueUserWorkItem(new WaitCallback(ShowSuccessNotificationAndCloseWindow));
        }

        private void ShowSuccessNotificationAndCloseWindow(object nullObject)
        {
            MessageBox.Show("Änderungen erfolgreich übertragen!");
            this.Close();
        }

        private void DeployChanges()
        {
            DirectoryInfo dirInfo = new DirectoryInfo(Properties.Settings.Default.DeploymentSourcePath);
            FileInfo[] fileInfos = GetAllFilesRecursively(dirInfo);
            for (int i = 0; i < fileInfos.Length; i++)
                ThreadPool.QueueUserWorkItem(new WaitCallback(CopyFileAndUpdateProgressBar), new FileCopyParameters(i, fileInfos));
        }

        private void CopyFileAndUpdateProgressBar(Object parameters)
        {
            try 
            { 
            int i = ((FileCopyParameters)parameters).Index;
            FileInfo[] fileInfos = ((FileCopyParameters)parameters).FileInfos;
            CreateDirectoryIfNecessary(GetRelativePath(fileInfos[i].Directory.FullName));
            File.Copy(fileInfos[i].FullName, Path.Combine(Properties.Settings.Default.DeploymentDestinationPath, GetRelativePath(fileInfos[i].FullName)), true);
            deploymentProgressBar.Value = (int)(((double)(i + 1) / (double)fileInfos.Length) * 100);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private void CreateDirectoryIfNecessary(string directory)
        {
            if (!Directory.Exists(Path.Combine(Properties.Settings.Default.DeploymentDestinationPath, directory)))
                Directory.CreateDirectory(Path.Combine(Properties.Settings.Default.DeploymentDestinationPath, directory));
        }

        private string GetRelativePath(string fileName)
        {
            return fileName.Replace(Properties.Settings.Default.DeploymentSourcePath,"");
        }

        private FileInfo[] GetAllFilesRecursively(DirectoryInfo dirInfo)
        {
            List<FileInfo> files = new List<FileInfo>();
            files.AddRange(GetAllFilesExceptThumbsDb(dirInfo.GetFiles()));
            foreach (DirectoryInfo directory in dirInfo.GetDirectories())
                files.AddRange(GetAllFilesRecursively(directory));
            return files.ToArray();
        }

        private List<FileInfo> GetAllFilesExceptThumbsDb(FileInfo[] fileInfos)
        {
            List<FileInfo> files = new List<FileInfo>();
            foreach (FileInfo fileInfo in fileInfos)
                if (!fileInfo.FullName.ToLowerInvariant().Contains("thumbs.db"))
                    files.Add(fileInfo);
            return files;
        }

        private class FileCopyParameters
        {
            public int Index { get; set; }
            public FileInfo[] FileInfos { get; set; }

            public FileCopyParameters(int index, FileInfo[] fileInfos)
            {
                this.Index = index;
                this.FileInfos = fileInfos;
            }
        }
    }
}
