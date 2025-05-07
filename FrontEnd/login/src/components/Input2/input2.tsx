type Props = {
  placeholder: string;
  type: string;
  name: string;
  img: string;
};

const input2 = ({ placeholder, type, name, img }: Props) => {
  return (
    <div className="flex w-[350px] sm:w-[380px] md:w-[310px] lg:w-11/12 relative items-center">
      <img
        src={img}
        alt="icon"
        className="absolute h-6 w-6 inset-y-auto left-[1vh]"
      />
      <input
        placeholder={placeholder}
        type={type}
        name={name}
        className={`w-full h-6 rounded-full border-black bg-white text-[#6D6D6D] p-4 text-[13px] font-mulish border-[1px] md:pl-[2.5rem] pl-[2rem] px-5`}
      />
    </div>
  );
};

export default input2;
