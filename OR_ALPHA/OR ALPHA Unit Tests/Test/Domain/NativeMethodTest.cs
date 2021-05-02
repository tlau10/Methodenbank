using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OrAlpha.Main.DomainMethodenbank;

namespace OrAlpha.Test.Domain
{
    [TestClass]
    public class NativeMethodTest
    {
        private const string executionPath = "C:\\Methodendatenbank\\Methoden\\PowerLP_NEU\\EXEC\\powerlp_neu.exe";

        [TestMethod]
        public void ShouldSetExecutionPath()
        {
            // given
            NativeMethod method = new NativeMethod();

            // when
            method.ExecutionPath = executionPath;

            // then
            Assert.AreEqual(executionPath, method.ExecutionPath);
        }
    }
}
