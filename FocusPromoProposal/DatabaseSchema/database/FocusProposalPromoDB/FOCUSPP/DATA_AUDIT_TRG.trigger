<?xml version = '1.0' encoding = 'UTF-8'?>
<trigger xmlns="http://xmlns.oracle.com/jdeveloper/1111/offlinedb">
  <ID class="oracle.javatools.db.IdentifierBasedID">
    <identifier class="java.lang.String">6b2138d8-d133-415a-abcf-6d1c538abc7e</identifier>
  </ID>
  <name>DATA_AUDIT_TRG</name>
  <baseType>TABLE</baseType>
  <code>BEGIN
  &lt;&lt;COLUMN_SEQUENCES&gt;&gt;
  BEGIN
    SELECT DATA_AUDIT_SEQ.NEXTVAL INTO :NEW.AUDIT_ID FROM DUAL;
  END COLUMN_SEQUENCES;
END;</code>
  <enabled>true</enabled>
  <events>
    <event>INSERT</event>
  </events>
  <schema>
    <name>FOCUSPP</name>
  </schema>
  <source>CREATE TRIGGER DATA_AUDIT_TRG 
BEFORE INSERT ON DATA_AUDIT 
FOR EACH ROW 
BEGIN
  &lt;&lt;COLUMN_SEQUENCES&gt;&gt;
  BEGIN
    SELECT DATA_AUDIT_SEQ.NEXTVAL INTO :NEW.AUDIT_ID FROM DUAL;
  END COLUMN_SEQUENCES;
END;</source>
  <statementLevel>false</statementLevel>
  <tableID class="oracle.javatools.db.IdentifierBasedID">
    <name>DATA_AUDIT</name>
    <identifier class="java.lang.String">3ba534e4-1b2a-482b-8f74-0f1ab6bd1932</identifier>
    <schemaName>FOCUSPP</schemaName>
    <type>TABLE</type>
  </tableID>
  <timing>BEFORE</timing>
</trigger>
