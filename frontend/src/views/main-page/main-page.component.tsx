import React from "react";

// templates
import TemplateView from "../../templates/view/view.template";

// components
import MainPageContent from "./content/main-page-content.component";

// interfaces
interface IViewMainPage {
   appVersion: string;
}

const ViewMainPage: React.FC<IViewMainPage> = ({appVersion}) => {

   return (
      <TemplateView
        appVersion={appVersion}
        viewTitle=""
        hasTopBar
      >
         <MainPageContent/>
      </TemplateView>
   );
};

export default ViewMainPage;