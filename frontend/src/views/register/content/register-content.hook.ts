import React, {useState} from "react";
import axios from "axios";

// data
import {initialRegisterForm} from "./register-content.data";
import {emailRgx} from "../../../data/email-regex";

// interfaces
import {IRegisterForm} from "./register-content.types";

const REGISTER_USER_API_URL = process.env.REACT_APP_BACKEND_API_URL !== undefined ?
    `${process.env.REACT_APP_BACKEND_API_URL}auth/register`
  :
    null;

const useRegisterContent = () => {
  const [registerForm, setRegisterForm] = useState<IRegisterForm>(initialRegisterForm);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const [successMessage, setSuccessMessage] = useState<string>("");
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [isSuccessfullySend, setIsSuccessfullySend] = useState<boolean>(false);

  const onInputChange = (name: string, value: string) => {
    setRegisterForm(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const validateData = () => {
    let isError = false;

    if (registerForm.username.length === 0) {
      isError = true;
      setErrorMessage("Nazwa użytkownika nie może być pusta");
    }

    if (!emailRgx.test(registerForm.email)) {
      isError = true;
      setErrorMessage("Zły format adresu email");
    }

    if (registerForm.password.localeCompare(registerForm.passwordRepeat) !== 0) {
      isError = true;
      setErrorMessage("Podane hasła są różne");
    }

    if (registerForm.password.length === 0) {
      isError = true;
      setErrorMessage("Hasło nie może być puste");
    }

    return !isError;
  };

  const onSubmit = async(e: React.FormEvent) => {
    e.preventDefault();

    if (validateData() && !isSuccessfullySend) {
      // Perform addition of item
      if (REGISTER_USER_API_URL) {
        setIsLoading(true);
        setErrorMessage("");
        setSuccessMessage("");

        try {
          await axios.post(REGISTER_USER_API_URL, {
            username: registerForm.username,
            email: registerForm.email,
            password: registerForm.password,
            firstName: registerForm.firstName,
            lastName: registerForm.lastName
          }, {
            headers: {
              "Content-Type": "application/json"
            }
          });

          setSuccessMessage("Poprawnie zarejestrowano użytkownika");
          setIsSuccessfullySend(true);
        }
        catch(e: any) {
          if (e.response) {
            const data = e.response.data;

            if (data.error) {
              setErrorMessage(data.message);
            }
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
    registerForm,
    onInputChange,
    onSubmit,
    errorMessage,
    setErrorMessage,
    successMessage,
    setSuccessMessage,
    isLoading,
    isSuccessfullySend
  };
};

export default useRegisterContent;