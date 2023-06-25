import React from "react";

// styles
import styles from "./main-page-content.module.scss";

// hooks
import useMainPageContent from "./main-page-content.hook";

// components
import LoadingModal from "../../../modals/loading-modal/loading-modal.component";
import FilterBar from "./filter-bar/filter-bar.component";
import TodoItem from "./todo-item/todo-item.component";

const MainPageContent: React.FC = () => {
  const {
    items,
    isLoading,
    isErrorDuringFetch,
    filterValues,
    handleOnFilterChange,
    locallyAddItem,
    locallyChangeItemAtIndex,
    locallyDeleteItemAtIndex
  } = useMainPageContent();

  return (
    <div className={styles.wrapper}>
      {/* Title */}
      <div className={styles.titleWrapper}>
        <p>Todo list</p>
      </div>
      {/* Filter bar (has "addItem" button) */}
      <div className={styles.filterBarWrapper}>
        <FilterBar
          filterValues={filterValues}
          handleOnFilterChange={handleOnFilterChange}
          locallyAddItem={locallyAddItem}
        />
      </div>
      {/* Item list (with "itemWrapper") */}
      {/*Note1: When item changes, edit bar is hiding AND edit fields have new values like changed item from DB (in inner useEffect) */}
      {/*Note2: When it's created new item or item changes, fetch new data via "fetchData" method. Only when data is deleted, it deletes locally*/}
      <div className={styles.itemListWrapper}>
        {
          !isErrorDuringFetch ?
            items.length > 0 ?
              // Show items via map method
              items.map((item, idx) => (
                <div
                  key={item.id}
                  className={styles.itemWrapper}
                >
                  <TodoItem
                    item={item}
                    locallyChangeItem={(value) => locallyChangeItemAtIndex(idx, value)}
                    locallyDeleteItem={() => locallyDeleteItemAtIndex(idx)}
                  />
                </div>
              ))
            :
              // Found zero items message
              <p className={styles.par}>Nie znaleziono zadań</p>
          :
            // Show message bar with error
            <p className={styles.par}>Wystąpił błąd poczas pobierania zadań</p>
        }
        {
          isLoading ?
            <LoadingModal/>
          :
            null
        }
      </div>
    </div>
  );
};

export default MainPageContent;