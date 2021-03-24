<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head />
            <body>
                <xsl:for-each select="LinearProgram">
                    <xsl:for-each select="Input">
                        <br />
                        <h1>
                            <table bgcolor="green" border="0" width="100%">
                                <tbody>
                                    <tr>
                                        <td>
                                            <span style="color:#FFFFFF; font-size:xx-large; ">Eingabe</span>
                                            <xsl:for-each select="/">
                                                <span style="color:#FFFFFF; font-size:xx-large; ">
                                                    <xsl:for-each select="LinearProgram">
                                                        <xsl:for-each select="@sourceFormat"> (<xsl:value-of select="." />)</xsl:for-each>
                                                    </xsl:for-each>
                                                </span>
                                            </xsl:for-each>
                                            <span style="color:#FFFFFF; font-size:xx-large; ">:</span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h1>
                        <h2>Restriktionen:</h2>
                        <xsl:for-each select="Constraint">
                            <p>
                                <table border="0" width="100%">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <xsl:for-each select="@name">
                                                    <xsl:value-of select="." />
                                                </xsl:for-each>:</td>
                                            <td width="90%">
                                                <xsl:for-each select="Variable"> + <xsl:apply-templates />&#160;<xsl:for-each select="@name">
                                                        <xsl:value-of select="." />
                                                    </xsl:for-each>
                                                </xsl:for-each>&#160;<xsl:for-each select="Operator">
                                                    <xsl:apply-templates />
                                                </xsl:for-each>&#160;<xsl:for-each select="b-Value">
                                                    <xsl:apply-templates />
                                                </xsl:for-each>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </p>
                        </xsl:for-each>
                        <br />
                        <h2>Zielfunktion:</h2>
                        <xsl:for-each select="ObjectiveFunction">
                            <table border="0" width="100%">
                                <tbody>
                                    <tr>
                                        <td>
                                            <xsl:for-each select="Variable"> + <xsl:apply-templates />&#160;<xsl:for-each select="@name">
                                                    <xsl:value-of select="." />
                                                </xsl:for-each>
                                            </xsl:for-each>
                                            <xsl:if test="starts-with(  Optimization  , &quot;MAX&quot; )">-&gt; MAX!</xsl:if>
                                            <xsl:if test="starts-with(  Optimization  , &quot;MIN&quot; )">-&gt; MIN!</xsl:if>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>&#160; </xsl:for-each>
                    </xsl:for-each>
                    <br />
                    <br />
                    <h2>Nebenbedingungen:</h2>
                    <xsl:for-each select="Input">
                        <xsl:for-each select="Bound">
                            <p>
                                <xsl:for-each select="@name">
                                    <xsl:value-of select="." />
                                </xsl:for-each>
                                <xsl:for-each select="Operator">
                                    <xsl:apply-templates />
                                </xsl:for-each>
                                <xsl:for-each select="Value">
                                    <xsl:apply-templates />
                                </xsl:for-each>
                            </p>
                        </xsl:for-each>
                    </xsl:for-each>
                    <h1>
                        <h1>
                            <table bgcolor="green" border="0" width="100%">
                                <tbody>
                                    <tr>
                                        <td>
                                            <span style="color:#FFFFFF; font-size:xx-large; ">Ausgabe:</span>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </h1>
                    </h1>
                    <xsl:for-each select="Output">
                        <xsl:for-each select="Message">
                            <xsl:apply-templates />
                        </xsl:for-each>
                        <br />
                        <h2>Zielfunktionswert:</h2>
                        <xsl:for-each select="ObjectiveFunction-Value">
                            <xsl:apply-templates />
                        </xsl:for-each>
                        <br />
                        <h2>Variablen:</h2>
                        <xsl:for-each select="Variable-Value">
                            <p>
                                <xsl:for-each select="@name">
                                    <xsl:value-of select="." />
                                </xsl:for-each> = <xsl:apply-templates />
                            </p>
                        </xsl:for-each>
                        <br />
                        <h2>Dual-Variablen:</h2>
                        <xsl:for-each select="Dual-Value">
                            <p>
                                <xsl:for-each select="@name">
                                    <xsl:value-of select="." />
                                </xsl:for-each> = <xsl:apply-templates />
                            </p>
                        </xsl:for-each>
                    </xsl:for-each>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
