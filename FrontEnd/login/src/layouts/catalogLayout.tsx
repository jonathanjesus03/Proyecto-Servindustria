import { Outlet } from "react-router-dom";

type Props = {};

const catalogLayout = ({}: Props) => {
  return (
    <div
      className="min-h-[100vh] min-w-full bg-repeat bg-top"
      style={{
        backgroundImage:
          "url(/src/assets/Images/backgrounds/bg_three_lines_black.png)",
      }}
    >
      <Outlet />
    </div>
  );
};

export default catalogLayout;
