using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using OrAlpha.Main.DomainMethodenbank;

namespace OrAlpha.Test.Domain
{
    [TestClass]
    public class AdminTest
    {
        private const string userName = "grütz";
        private const string password = "geheim";

        private AdminUser user;

        [TestInitialize]
        public void SetUp()
        {
            user = new AdminUser();
        }

        [TestMethod]
        public void ShouldReturnHashedAdminUserName()
        {
            user.Name = userName;
            Assert.AreNotEqual(userName, user.Name);
        }

        [TestMethod]
        public void ShouldReturnHashedAdminPassword()
        {
            user.Password = password;
            Assert.AreNotEqual(password, user.Password);
        }

    }
}
