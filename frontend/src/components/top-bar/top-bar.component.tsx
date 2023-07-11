import React, {useContext} from "react";

// styles
import styles from "./top-bar.module.scss";

// contexts
import {LoginContext} from "../../providers/login/login-provider.component";
import {Link} from "react-router-dom";

const TopBar: React.FC = () => {
  const {isLoggedIn, username, logoutUser} = useContext(LoginContext);

  return (
    <div className={styles.container}>
      {
        isLoggedIn ?
          <div className={styles.infoBar}>
            <p>
              Zalogowany jako: {username}
            </p>
            <p
              className={styles.logoutButton}
              onClick={logoutUser}
            >
              Wyloguj się
            </p>
          </div>
        :
          <div className={styles.columnLink}>
            <Link to="/login">
              Zaloguj się
            </Link>
            <Link to="/register">
              Zarejestruj się
            </Link>
          </div>
      }
    </div>
  );
};

export default TopBar;