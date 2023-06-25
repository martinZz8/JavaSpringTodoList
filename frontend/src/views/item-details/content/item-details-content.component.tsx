import React from "react";
import {RouteComponentProps, withRouter} from "react-router";

// styles
import styles from "./item-details-content.module.scss";

// functions
import {getTwoDigitsMonthStr} from "../../../functions/get-two-digits-month-str";

// hooks
import useItemDetailsContent from "./item-details-content.hook";

// components
import LoadingModal from "../../../modals/loading-modal/loading-modal.component";

// interfaces
interface IItemDetailsContent extends RouteComponentProps<any> {

}

const ItemDetailsContent: React.FC<IItemDetailsContent> = ({match}) => {
  const {item, isLoading, isItemNotFound} =useItemDetailsContent(match.params.id);

  return (
    <div className={styles.wrapper}>
      {
        !isLoading ?
          !isItemNotFound ?
            item ?
              <>
                <p className={styles.header}>
                  {item.name}
                </p>
                <div className={styles.infoRow}>
                  <p className={styles.title}>
                    Id:
                  </p>
                  <p>
                    {item.id}
                  </p>
                </div>
                <div className={styles.infoRow}>
                  <p className={styles.title}>
                    Opis:
                  </p>
                  <p>
                    {item.description}
                  </p>
                </div>
                <div className={styles.infoRow}>
                  <p className={styles.title}>
                    Ukończone:
                  </p>
                  <p>
                    {item.done ? "tak" : "nie"}
                  </p>
                </div>
                <div className={styles.infoRow}>
                  <p className={styles.title}>
                    Data utworzenia:
                  </p>
                  <p>
                    {`${item.createdOn.getDate()}.${getTwoDigitsMonthStr(item.createdOn.getMonth()+1)}.${item.createdOn.getFullYear()} r.`}
                  </p>
                </div>
              </>
            :
              null
          :
            <p className={styles.message}>
              Nie znaleziono oferty o podanym id
            </p>
        :
          <LoadingModal
            message="Ładowanie..."
            bgSemiTransparent
          />
      }
    </div>
  );
};

export default withRouter(ItemDetailsContent);