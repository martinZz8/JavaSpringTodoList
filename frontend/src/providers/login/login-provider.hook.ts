import {useState, useEffect} from "react";

// functions
import isUserAuthenticated from "../../functions/is-user-authenticated";

// constants
const lsTokenKey = "token";
const lsUsernameKey = "username";
const initialBearerToken = "";
const initialUsername = "";

const useLoginProvider = () => {
  const [isLoggedIn, setIsLoggedIn] = useState<boolean | null>(null);
  const [bearerToken, setBearerToken] = useState<string>(initialBearerToken);
  const [username, setUsername] = useState<string>(initialUsername);

  useEffect(() => {
    const lsToken = localStorage.getItem(lsTokenKey);
    const lsUsername = localStorage.getItem(lsUsernameKey);

    if (lsToken && isUserAuthenticated(lsToken)) {
      setBearerToken(lsToken);
      setIsLoggedIn(true);

      if (lsUsername) {
        setUsername(lsUsername);
      }
    }
    else {
      localStorage.setItem(lsTokenKey, initialBearerToken);
      localStorage.setItem(lsUsernameKey, initialUsername);
      setIsLoggedIn(false);
    }
  },[]);

  const loginUser = (token: string, username: string) => {
    if (token && isUserAuthenticated(token)) {
      localStorage.setItem(lsTokenKey, token);
      localStorage.setItem(lsUsernameKey, username);

      setBearerToken(token);
      setUsername(username);
      setIsLoggedIn(true);
    }
  };

  const logoutUser = () => {
    localStorage.setItem(lsTokenKey, initialBearerToken);
    localStorage.setItem(lsUsernameKey, initialUsername);

    setBearerToken(initialBearerToken);
    setUsername(initialUsername);
    setIsLoggedIn(false);
  };

  return {
    isLoggedIn,
    bearerToken,
    username,
    loginUser,
    logoutUser
  };
};

export default useLoginProvider;