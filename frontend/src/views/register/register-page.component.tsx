import React from "react";

// templates
import TemplateView from "../../templates/view/view.template";

// components
import RegisterContent from "./content/register-content.component";

// interfaces
interface IViewRegister {
  appVersion: string;
}

const ViewRegister: React.FC<IViewRegister> = ({appVersion}) => {

  return (
    <TemplateView
      appVersion={appVersion}
      viewTitle="Register"
      hasTopBar
    >
      <RegisterContent/>
    </TemplateView>
  );
};

export default ViewRegister;