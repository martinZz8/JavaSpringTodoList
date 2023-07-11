import React from "react";

// styles
import styles from "./register-content.module.scss";

// hooks
import useRegisterContent from "./register-content.hook";

// components
import MessageBox from "../../../components/message-box/message-box.component";
import Button from "../../../components/ui/button/button.component";
import InputField from "../../../components/ui/input-field/input-field.component";
import LoadingModal from "../../../modals/loading-modal/loading-modal.component";
import {Link} from "react-router-dom";

const RegisterContent: React.FC = () => {
  const {
    registerForm,
    onInputChange,
    onSubmit,
    errorMessage,
    setErrorMessage,
    successMessage,
    setSuccessMessage,
    isLoading,
    isSuccessfullySend
  } = useRegisterContent();

  return (
    <div className={styles.wrapper}>
      <div className={styles.titleWrapper}>
        <p>Rejestracja</p>
      </div>
      <form
        className={styles.formWrapper}
        onSubmit={onSubmit}
        noValidate
      >
        <div className={styles.inputWrapper}>
          <InputField
            type="text"
            name="firstName"
            label="Imię"
            labelColor="black"
            value={registerForm.firstName}
            handleChange={onInputChange}
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.inputWrapper}>
          <InputField
            type="text"
            name="lastName"
            label="Nazwisko"
            labelColor="black"
            value={registerForm.lastName}
            handleChange={onInputChange}
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.inputWrapper}>
          <InputField
            type="text"
            name="username"
            label="Nazwa użytkownika"
            labelColor="black"
            value={registerForm.username}
            handleChange={onInputChange}
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.inputWrapper}>
          <InputField
            type="email"
            name="email"
            label="Email"
            labelColor="black"
            value={registerForm.email}
            handleChange={onInputChange}
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.inputWrapper}>
          <InputField
            type="password"
            name="password"
            label="Hasło"
            labelColor="black"
            value={registerForm.password}
            handleChange={onInputChange}
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.inputWrapper}>
          <InputField
            type="password"
            name="passwordRepeat"
            label="Powtórzenie hasła"
            labelColor="black"
            value={registerForm.passwordRepeat}
            handleChange={onInputChange}
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.buttonWrapper}>
          <Button
            type="submit"
            title={"Zarejestruj się"}
            fontColor="black"
            backgroundColor="lightPurple"
            bigFont
            disabled={isSuccessfullySend}
            bigHeight
          />
        </div>
        <div className={styles.loginInfoWrapper}>
          <p>
            Masz już konto? <Link to="/login">Zaloguj się</Link>
          </p>
        </div>
        {
          successMessage.length > 0 ?
            <div className={styles.messageWrap}>
              <MessageBox
                message={successMessage}
                onCloseClick={() => setSuccessMessage("")}
                wide
              />
            </div>
          : errorMessage.length > 0 ?
            <div className={styles.messageWrap}>
              <MessageBox
                message={errorMessage}
                onCloseClick={() => setErrorMessage("")}
                wide
                isError
              />
            </div>
          :
            null
        }
        {
          isLoading ?
            <LoadingModal
              message="Wysyłanie..."
              bgSemiTransparent
            />
          :
            null
        }
      </form>
    </div>
  );
};

export default RegisterContent;