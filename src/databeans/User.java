
package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

public class User  {
	private int    id          = -1;
	private String  userName = null;

	private String  hashedPassword = "*";
	private int     salt           = 0;

	private String  firstName      = null;
	private String  lastName       = null;
	private String  addr_line1       = null;
	private String  addr_line2       = null;
	private String  city       = null;
	private String  state       = null;
	private String  zip       = null;
	private double  cash       = 0;

	public boolean checkPassword(String password) {
		System.out.println(hashedPassword+" "+password+" "+hash(password)+" "+hashedPassword.equals(hash(password)));
		return hashedPassword.equals(hash(password));
	}
	
	public int compareTo(User other) {
		// Order first by lastName and then by firstName
		int c = lastName.compareTo(other.lastName);
		if (c != 0) return c;
		c = firstName.compareTo(other.firstName);
		if (c != 0) return c;
		return userName.compareTo(other.userName);
	}

	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User other = (User) obj;
			return userName.equals(other.userName);
		}
		return false;
	}

	public String  getHashedPassword() { return hashedPassword; }
	public String  getUserName()       { return userName;       }
	public int     getSalt()           { return salt;           }

	public String  getFirstName()      { return firstName;      }
	public String  getLastName()       { return lastName;       }
	public String  getAddr_line1()      { return addr_line1;      }
	public String  getAddr_line2()      { return addr_line2;      }
	public String  getCity()      { return city;      }
	public String  getState()      { return state;      }
	public String  getZip()      { return zip;      }
	public double  getCash()      { return cash;      }
	 public int    getId()          { return id;          }
	public int     hashCode()          { return userName.hashCode(); }

	public void setHashedPassword(String x)  { hashedPassword = x; }
	public void setPassword(String s)        { salt = newSalt(); hashedPassword = hash(s); }
	public void setSalt(int x)               { salt = x;           }

	public void setFirstName(String s)       { firstName = s;      }
	public void setLastName(String s)        { lastName = s;       }
	 public void    setId(int id)          {  this.id=id;          }
	public void setUserName(String s)        { userName = s;       }
	public void setAddr_line1(String s)        { addr_line1 = s;       }
	public void setAddr_line2(String s)        { addr_line2 = s;       }
	public void setCity(String s)        { city = s;       }
	public void setState(String s)        { state = s;       }
	public void setZip(String s)        { zip = s;       }
	public void setCash(double s)        { cash = s;       }

	public String toString() {
		return "User("+getUserName()+")";
	}

	private String hash(String clearPassword) {
		if (salt == 0) return null;

		MessageDigest md = null;
		try {
		  md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
		  throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);
		
		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
		byte[] digestBytes = md.digest();

		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i=0; i<digestBytes.length; i++) {
		  int lowNibble = digestBytes[i] & 0x0f;
		  int highNibble = (digestBytes[i]>>4) & 0x0f;
		  digestSB.append(Integer.toHexString(highNibble));
		  digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();

		return digestStr;
	}

	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192)+1;  // salt cannot be zero
	}
}
