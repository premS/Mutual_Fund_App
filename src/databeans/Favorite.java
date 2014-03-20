

package databeans;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class Favorite implements Comparable<Favorite> {
	public static final List<String> EXTENSIONS = Collections.unmodifiableList(Arrays.asList( new String[] {
			".jpg", ".gif", ".JPG"
	} ));

	private int    id          = -1;
	
	private String url       = null;
	private String caption     = null;
	private String owner       = null;
	private int  click_count    = 0;
	
	public int compareTo(Favorite other) {
		// Order first by owner, then by position
		if (owner == null && other.owner != null) return -1;
		if (owner != null && other.owner == null) return 1;
		int c = owner.compareTo(other.owner);
		if (c != 0) return c;
		return click_count - other.click_count;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Favorite) {
			Favorite other = (Favorite) obj;
			return compareTo(other) == 0;
		}
		return false;
	}
	
    public String getUrl()       { return url;       }
    public String getCaption()     { return caption;     }
   public int    getId()          { return id;          }
    public String getOwner()       { return owner;       }
    public int    getClickcount()    { return click_count;    }
    
    public void setUrl(String a)        { url = a;        }
    public void setCaption(String s)      { caption = s;      }
    public void setId(int x)              { id = x;           }
    public void setOwner(String userName) { owner = userName; }
    public void setClickcount(int p)        { click_count = p;     }
    
    public String toString() {
    	return "Photo("+id+")";
    }
}
