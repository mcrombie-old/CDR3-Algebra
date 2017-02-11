package CDR3Algebra;

import java.util.function.Predicate;

class StarFilterPredicate<t>
  implements Predicate<t>
{
  public boolean test(t t)
  {
    if (containsChar(t.toString(), '*')) {
      return true;
    }
    return false;
  }
  
  public boolean containsChar(String s, char search)
  {
    if (s.length() == 0) {
      return false;
    }
    return (s.charAt(0) == search) || (containsChar(s.substring(1), search));
  }
}
