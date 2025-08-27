import { Outlet } from "react-router-dom";

type Props = {};

const dashboardLayout = ({}: Props) => {
  return (
    <div
      className="min-h-[100vh] min-w-screen bg-cover bg-center bg-no-repeat"
      style={{
        backgroundImage:
          "url(/src/assets/Images/backgrounds/white_backgrounds/bg_white_dash.png)",
      }}
    >
      <Outlet />
    </div>
  );
};

export default dashboardLayout;
