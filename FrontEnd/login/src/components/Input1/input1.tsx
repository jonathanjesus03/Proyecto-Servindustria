type Props = {
  placeholder: string;
  type: string;
  name: string;
  img: string;
  pattern_in?: string;
  style?: string;
};

const input1 = ({ placeholder, type, name, img, pattern_in, style }: Props) => {
  return (
    <div className="flex w-[350px] sm:w-[380px] md:w-[480px] lg:w-11/12 relative items-center">
      <img
        src={img}
        alt="icon"
        className="absolute h-8 w-9 inset-y-auto left-[4vh]"
      />
      <input
        placeholder={placeholder}
        type={type}
        name={name}
        required
        pattern={pattern_in}
        className={`w-full h-12 rounded-full border-black bg-white text-[#6D6D6D] text-[14px] p-5 font-mulish border-[1px] md:pl-[4.5rem] pl-[4rem] px-5 invalid:[&:not(:placeholder-shown):not(:focus)]:bg-red-100 ${style} peer`}
      />

      <span className="mt-2 hidden text-[10px] text-red-500 peer-[&:not(:placeholder-shown):not(:focus):invalid]:block"></span>
    </div>
  );
};

export default input1;
