import React, {useContext, useState} from "react";
import axios from "axios";

// data
import {initialLoginForm} from "./login-content.data";

// contexts
import {LoginContext} from "../../../providers/login/login-provider.component";

// interfaces
import {ILoginForm} from "./login-content.types";

const LOGIN_USER_API_URL = process.env.REACT_APP_BACKEND_API_URL !== undefined ?
    `${process.env.REACT_APP_BACKEND_API_URL}auth/login`
  :
    null;

const useLoginContent = () => {
  const [loginForm, setLoginForm] = useState<ILoginForm>(initialLoginForm);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const {loginUser} = useContext(LoginContext);

  const onInputChange = (name: string, value: string) => {
    setLoginForm(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const validateData = () => {
    let isError = false;

    if (loginForm.username.length === 0) {
      isError = true;
      setErrorMessage("Nazwa użytkownika nie może być pusta");
    }

    if (loginForm.password.length === 0) {
      isError = true;
      setErrorMessage("Hasło nie może być puste");
    }

    return !isError;
  };

  const onSubmit = async(e: React.FormEvent) => {
    e.preventDefault();

    if (validateData()) {
      if (LOGIN_USER_API_URL) {
        try {
          setIsLoading(true);
          setErrorMessage("");

          const {data} = await axios.post(LOGIN_USER_API_URL, {
            username: loginForm.username,
            password: loginForm.password
          }, {
            headers: {
              "Content-Type": "application/json"
            }
          });

          loginUser(data.jwt, data.username);
        }
        catch(e: any) {
          if (e.response) {
            setErrorMessage("Podano złe dane logowania.");
          }
          else {
            setErrorMessage("Wystąpił nieoczekiwany błąd. Spróbuj ponownie później.");
          }

        }
        finally {
          setIsLoading(false);
        }
      }
    }
  };

  return {
    loginForm,
    onInputChange,
    onSubmit,
    errorMessage,
    setErrorMessage,
    isLoading
  };
};

export default useLoginContent;