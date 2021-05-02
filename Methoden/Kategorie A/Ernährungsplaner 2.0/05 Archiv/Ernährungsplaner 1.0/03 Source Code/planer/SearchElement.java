package planer;


public class SearchElement
{
  private String valueString_;
  private String elementString_;

  public SearchElement()
  {
    valueString_ = "";
    elementString_ = "";
  }

  public String getValueString()
  {
    return valueString_;
  }

  public void setValueString(String valueString)
  {
    valueString_ = valueString;
  }
   public String getElementString()
  {
    return elementString_;
  }

  public void setElementString(String elementString)
  {
    elementString_ = elementString;
  }
}