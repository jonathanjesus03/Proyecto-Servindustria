import { Outlet } from "react-router-dom";

export default function LoginLayout() {
  return (
    <div
      className="min-h-[100vh] min-w-[100vh] flex items-center justify-center bg-cover bg-center bg-no-repeat"
      style={{
        backgroundImage: "url(/src/assets/Images/backgrounds/bg_log.png)",
      }}
    >
      <Outlet />
    </div>
  );
}
