import React, {useContext} from 'react';

// styles
import styles from "./Root.module.scss";

// hooks
import useRoot from "./useRoot";

// contexts
import {LoginContext} from "../../providers/login/login-provider.component";

// components
import AuthenticatedApp from "./routes/authenticated-app.component";
import UnauthenticatedApp from "./routes/unauthenticated-app.component";

function Root() {
  const {appVersion} = useRoot();
  const {isLoggedIn} = useContext(LoginContext);

  return (
    <div className={`${styles.wrapper} basicColors`}>
      {
        isLoggedIn !== null ?
          isLoggedIn ?
            <AuthenticatedApp appVersion={appVersion}/>
          :
            <UnauthenticatedApp appVersion={appVersion}/>
        :
          null
      }
    </div>
  );
}

export default Root;
