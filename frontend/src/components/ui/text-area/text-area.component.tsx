import React from "react";
import type {ChangeEvent} from "react";

// styles
import styles from "./text-area.module.scss";

// interfaces
interface ITextArea {
  name: string;
  value: string;
  label?: string;
  placeholder: string;
  isError?: boolean;
  errorMessage?: string;
  handleChange: (name: string, value: string) => void;
  disabled?: boolean;
  noErrorBar?: boolean;
}

const TextArea: React.FC<ITextArea> = ({
    name, value, label, placeholder, isError, errorMessage, handleChange, disabled, noErrorBar
  }) => {

  return (
    <div className={styles.textAreaWrap}>
      {
        label ?
          <div className={styles.label}>
            <p>{label}</p>
          </div>
        :
          null
      }
      <textarea
        className={`customScrollBar ${styles.textArea} ${isError && !disabled ? styles.errorTextArea : ""} ${disabled ? styles.disabled : ""}`}
        name={name}
        value={value}
        placeholder={placeholder}
        onChange={(e:ChangeEvent<HTMLTextAreaElement>) => handleChange(e.target.name, e.target.value)}
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

export default TextArea;