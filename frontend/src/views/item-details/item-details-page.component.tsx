import React from "react";
import {RouteComponentProps, withRouter} from "react-router";

// templates
import TemplateView from "../../templates/view/view.template";

// components
import ItemDetailsContent from "./content/item-details-content.component";

// interfaces
interface IViewMainPage extends RouteComponentProps<any> {
  appVersion: string;
}

const ViewItemDetails: React.FC<IViewMainPage> = ({appVersion, match}) => {
  // Or use "useParams" from "react-router-dom"
  //const {id} = useParams<{id: string}>();

  return (
    <TemplateView
      appVersion={appVersion}
      viewTitle={`Todo with id ${match.params.id}`}
      hasTopBar
    >
      <ItemDetailsContent/>
    </TemplateView>
  );
};

export default withRouter(ViewItemDetails);