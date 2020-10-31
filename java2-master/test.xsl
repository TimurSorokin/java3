<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/">
<html> 
<body>
  <h2>Biblioteca Municipal</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th style="text-align:left">TITLE</th>
      <th style="text-align:left">AUTHOR</th>
  <th style="text-align:left">ISBN</th>
  <th style="text-align:left">YEAR</th>
  <th style="text-align:left">PULISHER</th>
  <th style="text-align:left">PAGES</th>
    </tr>
    <xsl:for-each select="library/book">
    <tr>
      <td><xsl:value-of select="title"/></td>
      <td><xsl:value-of select="author"/></td>
 <td><xsl:value-of select="isbn"/></td>
 <td><xsl:value-of select="year"/></td>
 <td><xsl:value-of select="publisher"/></td>
 <td><xsl:value-of select="pages"/></td>

    </tr>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>

