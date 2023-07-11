import React from "react";

// styles
import styles from "./login-content.module.scss";

// hooks
import useLoginContent from "./login-content.hook";
import InputField from "../../../components/ui/input-field/input-field.component";
import Button from "../../../components/ui/button/button.component";
import {Link} from "react-router-dom";
import MessageBox from "../../../components/message-box/message-box.component";
import LoadingModal from "../../../modals/loading-modal/loading-modal.component";

const LoginContent: React.FC = () => {
  const {
    loginForm,
    onInputChange,
    onSubmit,
    errorMessage,
    setErrorMessage,
    isLoading
  } = useLoginContent();

  return (
    <div className={styles.wrapper}>
      <div className={styles.titleWrapper}>
        <p>Logowanie</p>
      </div>
      <form
        className={styles.formWrapper}
        onSubmit={onSubmit}
        noValidate
      >
        <div className={styles.inputWrapper}>
          <InputField
            type="text"
            name="username"
            label="Nazwa użytkownika"
            labelColor="black"
            value={loginForm.username}
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
            value={loginForm.password}
            handleChange={onInputChange}
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.buttonWrapper}>
          <Button
            type="submit"
            title={"Zaloguj się"}
            fontColor="black"
            backgroundColor="lightPurple"
            bigFont
            bigHeight
          />
        </div>
        <div className={styles.loginInfoWrapper}>
          <p>
            Nie masz konta? <Link to="/register">Zarejestruj się</Link>
          </p>
        </div>
        {
            errorMessage.length > 0 ?
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

export default LoginContent;