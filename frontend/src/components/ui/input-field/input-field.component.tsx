import React from "react";
import type {ChangeEvent} from "react";

// styles
import styles from "./input-field.module.scss";

// interfaces
interface IInputField {
  type: "text" | "password" | "email" | "number" | "date" | "datetime-local";
  name: string;
  value: string;
  label?: string;
  labelColor: "white" | "black";
  placeholder: string;
  isError?: boolean;
  errorMessage?: string;
  handleChange: (name: string, value: string) => void;
  disabled?: boolean;
  isBorderBlack?: boolean;
  noErrorBar?: boolean;
}

const InputField: React.FC<IInputField> = ({
    type,
    name,
    value,
    label,
    labelColor,
    placeholder,
    isError,
    errorMessage,
    handleChange,
    disabled,
    isBorderBlack,
    noErrorBar
  }) => {

  return (
    <div className={styles.inputField}>
      {
        label ?
          <div className={`${styles.label} ${labelColor === "white" ? styles.whiteLabel : ""}`}>
            <p>{label}</p>
          </div>
        :
          null
      }
      <input
        className={`
          ${styles.input}
          ${isError && !disabled ? styles.errorInput : ""}
          ${disabled ? styles.disabled : ""}
          ${isBorderBlack ? styles.borderBlack : ""}
        `}
        type={type}
        name={name}
        value={value}
        placeholder={placeholder}
        onChange={(e: ChangeEvent<HTMLInputElement>) => handleChange(e.target.name, e.target.value)}
        disabled={disabled}
      />
      {
        !noErrorBar ?
          <div className={styles.errorMessage}>
            {
              isError && errorMessage !== "" && !disabled ?
                <p>{errorMessage}</p>
              :
                null
            }
          </div>
        :
          null
      }
    </div>
  );
};

export default InputField;