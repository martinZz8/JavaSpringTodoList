import React, {createContext} from "react";

// hooks
import useLoginProvider from "./login-provider.hook";

export interface ILoginContext {
  isLoggedIn: boolean | null;
  bearerToken: string;
  username: string;
  loginUser: (token: string, username: string) => void;
  logoutUser: () => void;
}

const innerLoginContext: ILoginContext = {
  isLoggedIn: null,
  bearerToken: "",
  username: "",
  loginUser: (token, username) => undefined,
  logoutUser: () => undefined
};

export const LoginContext = createContext(innerLoginContext);

interface ILoginProvider {
  children: React.ReactNode;
}

const LoginProvider: React.FC<ILoginProvider> = ({children}) => {
  const {isLoggedIn, bearerToken, username, loginUser, logoutUser} = useLoginProvider();

  return (
    <LoginContext.Provider
      value={{
        isLoggedIn: isLoggedIn,
        bearerToken: bearerToken,
        username: username,
        loginUser: loginUser,
        logoutUser: logoutUser
      }}
    >
      {children}
    </LoginContext.Provider>
  );
};

export default LoginProvider;