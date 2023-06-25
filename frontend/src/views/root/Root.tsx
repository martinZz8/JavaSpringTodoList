import React from 'react';

// styles
import styles from "./Root.module.scss";

// hooks
import useRoot from "./useRoot";

// components
import StandardApp from "./routes/standard-app.component";

function Root() {
  const {appVersion} = useRoot();
  return (
    <div className={`${styles.wrapper} basicColors`}>
      <StandardApp appVersion={appVersion}/>
    </div>
  );
}

export default Root;
