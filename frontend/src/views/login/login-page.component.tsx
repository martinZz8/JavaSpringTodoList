import React from "react";

// templates
import TemplateView from "../../templates/view/view.template";

// components
import LoginContent from "./content/login-content.component";

// interfaces
interface IViewLogin {
  appVersion: string;
}

const ViewLogin: React.FC<IViewLogin> = ({appVersion}) => {

  return (
    <TemplateView
      appVersion={appVersion}
      viewTitle="Login"
      hasTopBar
    >
      <LoginContent/>
    </TemplateView>
  );
};

export default ViewLogin;