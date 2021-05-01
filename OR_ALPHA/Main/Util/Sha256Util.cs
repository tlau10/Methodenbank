using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace OrAlpha.Main.Util
{
    public class Sha256Util
    {
        private static SHA256 sha256 = new SHA256Managed();

        public static string GetHashValue(string input)
        {
            byte[] inputBytes = Encoding.Default.GetBytes(input);
            return ComputeStringValue(sha256.ComputeHash(inputBytes));
        }

        private static string ComputeStringValue(byte[] hashBytes)
        {
            string hashString = string.Empty;
            foreach (byte hashByte in hashBytes)
                hashString += hashByte.ToString("X");
            return hashString;
        }
    }
}
