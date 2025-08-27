import { Outlet } from "react-router-dom";

export default function LoginLayout() {
  return (
    <div
      className="min-h-[100vh] min-w-screen bg-cover bg-center bg-no-repeat flex items-start"
      style={{
        backgroundImage: "url(/src/assets/Images/backgrounds/bg_log.png)",
      }}
    >
      <Outlet />
    </div>
  );
}
