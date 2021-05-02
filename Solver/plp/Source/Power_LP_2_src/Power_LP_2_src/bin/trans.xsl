<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="CPLEXSolution">
		<xsl:value-of select="concat('Objective value: ',header/@objectiveValue
							)"></xsl:value-of>
-----------
Variables:
-----------
<xsl:for-each select="variables/variable">
---
Variable <xsl:value-of select="concat(
		 					@name, ': '
							,' '
							,'index: ',@index
							,', '
							,'status: ',@status
							,', '
							,'value: ',@value
							,', '
							,'reducedCost: ',@reducedCost
							,'&#xa;'
							)"/>
</xsl:for-each>
-------------
Constraints:
-------------
		 <xsl:for-each select="linearConstraints/constraint">
---
Constraint <xsl:value-of select="concat(
		 					@name, ': '
							,' '
							,'index: ',@index
							,', '
							,'status: ',@status
							,', '
							,'slack: ',@slack
							,', '
							,'dual: ',@dual
							,'&#xa;'
							)"/>
		</xsl:for-each>
		
	</xsl:template>
</xsl:stylesheet>