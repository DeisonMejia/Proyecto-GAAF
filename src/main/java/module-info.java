module com.gaaf {
  requires javafx.controls;
  requires java.sql;

  exports com.gaaf;
  opens com.gaaf to javafx.base;
}