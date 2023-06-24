import React from "react";
import { Redirect, Route, Switch } from "react-router-dom";

// views
import ViewMainPage from "../../main-page/main-page.component";

// interfaces
interface IStandardApp {
  appVersion: string;
}

const StandardApp: React.FC<IStandardApp> = ({appVersion}) => {

  return (
    <Switch>
      {/*TODO Td item page*/}
      {/*<Route*/}
      {/*  exact*/}
      {/*  path="/item/:id"*/}
      {/*  component={() => (*/}
      {/*    <ViewTdItem appVersion={appVersion} />*/}
      {/*  )*/}
      {/*  }*/}
      {/*/>*/}
      {/*Main page*/}
      <Route
        exact
        path="/"
        component={() => (
            <ViewMainPage appVersion={appVersion} />
          )
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

export default StandardApp;