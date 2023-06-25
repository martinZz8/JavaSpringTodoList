import React from "react";

// styles
import styles from "./filter-bar.module.scss";

// data
import {selectIsDoneOptions, selectIsOrderByDateAscendingOptions} from "../main-page-content.data";

// hooks
import useFilterBar from "./filter-bar.hook";

// components
import Button from "../../../../components/ui/button/button.component";
import InputField from "../../../../components/ui/input-field/input-field.component";
import Select from "../../../../components/ui/select/select.component";
import AddItemModal from "../add-item-modal/add-item-modal.component";

// interfaces
import {IFilterValues, IItem} from "../main-page-content.types";

interface IFilterBar {
  filterValues: IFilterValues;
  handleOnFilterChange: (name: string, value: string) => void;
  locallyAddItem: (item: IItem) => void;
}

const FilterBar: React.FC<IFilterBar> = ({
    filterValues,
    handleOnFilterChange,
    locallyAddItem
 }) => {
  const {
    isAddItemModalOpened,
    setIsAddItemModalOpened
  } = useFilterBar();

  return (
    <div className={styles.wrapper}>
      <form
        className={styles.filterInputsWrap}
        onSubmit={(e) => {
          e.preventDefault();
          // Fetch actions performs on every change of input
        }}
        noValidate
      >
        <div className={styles.inputNameWrap}>
          <InputField
            type="text"
            value={filterValues.name}
            handleChange={handleOnFilterChange}
            name="name"
            label="Nazwa"
            placeholder="-"
            labelColor="black"
            noErrorBar
          />
        </div>
        <div className={styles.selectIsDoneWrap}>
          <Select
            label="Status"
            options={selectIsDoneOptions}
            value={filterValues.isDoneStr}
            handleChange={handleOnFilterChange}
            name="isDoneStr"
            placeholder="-"
            noErrorBar
          />
        </div>
        <div className={styles.selectOrderByDateWrap}>
          <Select
            label="Sortowanie (data)"
            options={selectIsOrderByDateAscendingOptions}
            value={filterValues.isAscendingStr}
            handleChange={handleOnFilterChange}
            name="isAscendingStr"
            placeholder="-"
            noErrorBar
          />
        </div>
      </form>
      <div className={styles.addButtonWrap}>
        <Button
          type="button"
          title="Dodaj"
          fontColor="black"
          backgroundColor="lightPurple"
          handleClick={() => setIsAddItemModalOpened(true)}
          isBoxShadow
          bigFont
        />
      </div>
      <AddItemModal
        isOpened={isAddItemModalOpened}
        closeModal={() => setIsAddItemModalOpened(false)}
        locallyAddItem={locallyAddItem}
      />
    </div>
  );
};

export default FilterBar;