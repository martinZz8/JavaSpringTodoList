import React, {ReactNode} from "react";
import {Helmet} from "react-helmet-async";

// styles
import styles from "./view.module.scss";

// components
import Footer from "../../components/footer/footer.component";
import BackToTopButton from "../../components/back-to-top-button/back-to-top-button.component";
import TopBar from "../../components/top-bar/top-bar.component";

// interface
interface ITemplateView {
   viewTitle?: string;
   appVersion: string;
   hasTopBar?: boolean
   children: ReactNode;
}

const TemplateView: React.FC<ITemplateView> = ({
    viewTitle,
    appVersion,
    hasTopBar,
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
            {/*Top Bar*/}
            {
              hasTopBar ?
                <TopBar/>
              :
                null
            }
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

export default TemplateView;