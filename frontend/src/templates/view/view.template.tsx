import React, {ReactNode} from "react";
import {RouteComponentProps, withRouter} from "react-router";
import {Helmet} from "react-helmet-async";

// styles
import styles from "./view.module.scss";

// components
import Footer from "../../components/footer/footer.component";
import BackToTopButton from "../../components/back-to-top-button/back-to-top-button.component";

// interface
interface ITemplateView extends RouteComponentProps<any> {
   viewTitle?: string;
   appVersion: string;
   children: ReactNode;
}

const TemplateView: React.FC<ITemplateView> = ({
      viewTitle,
      appVersion,
      children
   }) => {

   return (
      <>
          <Helmet>
            <title>
               {
                  typeof(viewTitle) !== "undefined" ?
                     viewTitle.length > 0 ?
                        `${viewTitle} - `
                     :
                        ""
                  :
                     ""
               }
               {appVersion}
            </title>
          </Helmet>
          <div className={styles.app}>
            {/*App Menu*/}
            {/*App Content*/}
            <div className={styles.appContent}>
              {children}
            </div>
            {/*App Footer*/}
            <Footer/>
            {/* Back to top button*/}
            <BackToTopButton/>
          </div>
      </>
   );
};

export default withRouter(TemplateView);