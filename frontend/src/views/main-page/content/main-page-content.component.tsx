import React from "react";

// styles
import styles from "./main-page-content.module.scss";

// hooks
import useMainPageContent from "./main-page-content.hook";
import LoadingModal from "../../../modals/loading-modal/loading-modal.component";

// components


const MainPageContent: React.FC = () => {
  const {
    items,
    isLoading,
    isErrorDuringFetch,
    filterValues,
    handleOnFilterChange,
    fetchData
  } = useMainPageContent();

  return (
    <div className={styles.wrapper}>
      {/* Filter bar (has "addItem" button)*/}
      <div className={styles.filterBarWrapper}>
        {/* TODO */}
      </div>
      {/* Item list (with "itemWrapper")*/}
      {/*Note1: When item changes, edit bar is hiding AND edit fields have new values like changed item from DB (in inner useEffect) */}
      {/*Note2: When it's created new item or item changes, fetch new data via "fetchData" method. Only when data is deleted, it deletes locally*/}
      <div className={styles.itemListWrapper}>
        {
          !isErrorDuringFetch ?
            items.length > 0 ?
              //TODO Show items via map method
              <></>
            :
              // Found zero items message
              <p>Nie znaleziono zadań</p>
          :
            //TODO Show message bar with error
            <></>
        }
        {
          isLoading ?
            <LoadingModal/>
          :
            null
        }
      </div>
      {/* Add item modal */}
      {/* TODO */}
    </div>
  );
};

export default MainPageContent;