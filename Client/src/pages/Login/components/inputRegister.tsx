import React from "react";

type Props = {
  label: string;
  name: string;
  placeholder: string;
  type?: string;
  icon: string;
  value?: string;
  error?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
};

function inputRegister({
  label,
  name,
  placeholder,
  icon,
  type = "text",
  value,
  error,
  onChange,
}: Props) {
  return (
    <div className="flex flex-col space-y-[2px] justify-center items-center">
      <label htmlFor={name} className="SMALL font-light text-[#5C7085]">
        {label}
      </label>
      <div className="flex w-[350px] sm:w-[480px] md:w-[280px] lg:w-11/12 relative items-center">
        <img src={icon} alt="icon" className="logoRegister" />
        <input
          placeholder={placeholder}
          type={type}
          name={name}
          value={value}
          onChange={onChange}
          className="inputRegister"
        />
        {error && (
          <div className="absolute top-full left-0 mt-1 bg-red-100 text-red-600 text-xs px-3 py-1 rounded-md shadow-md z-10">
            {error}
          </div>
        )}
      </div>
    </div>
  );
}

export default inputRegister;
