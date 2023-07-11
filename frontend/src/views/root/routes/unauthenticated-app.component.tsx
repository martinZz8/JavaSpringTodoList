import React from "react";
import { Redirect, Route, Switch } from "react-router-dom";

// views
import ViewMainPage from "../../main-page/main-page.component";
import ViewLogin from "../../login/login-page.component";
import ViewRegister from "../../register/register-page.component";

// interfaces
interface IUnauthenticatedApp {
  appVersion: string;
}

const UnauthenticatedApp: React.FC<IUnauthenticatedApp> = ({appVersion}) => {

  return (
    <Switch>
      {/*Login page*/}
      <Route
        exact
        path="/login"
        component={() =>
          <ViewLogin appVersion={appVersion} />
        }
      />
      {/*Register page*/}
      <Route
        exact
        path="/register"
        component={() =>
          <ViewRegister appVersion={appVersion} />
        }
      />
      {/*Main page*/}
      <Route
        exact
        path="/"
        component={() =>
          <ViewMainPage appVersion={appVersion} />
        }
      />
      {/*Other routes*/}
      <Route
        exact
        path="*"
        component={() =>
          <Redirect to={"/"}/>
        }
      />
    </Switch>
  );
};

export default UnauthenticatedApp;