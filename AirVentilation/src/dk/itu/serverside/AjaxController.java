package dk.itu.serverside;

import java.awt.List;
import java.awt.image.ConvolveOp;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.spi.FormatConversionProvider;

import com.google.gson.Gson;

import dk.itu.restconnection.Measurement;

@WebServlet("/ajaxcontroller")
public class AjaxController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public AjaxController(){}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException
	{
		try {
			String commandname = request.getParameter("cmd");
			
			Command command = ApplicationController.getInstance().getCommand(commandname);
			command.execute(request);
			
			Gson gson = new Gson();
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();

			if(commandname.equals("getRecommendedAction"))
			{
				String action = (String)request.getAttribute("RecommendedAction");
				out.print(gson.toJson(action));
			}
			
			if(commandname.equals("getHeaterGain"))
			{
				Measurement[] gainValue = (Measurement[])request.getAttribute("HeaterGain");
				out.print(gson.toJson(gainValue));
			}

			if(commandname.equals("getCurrentHeaterGain"))
			{
				Measurement[] gainValue = (Measurement[])request.getAttribute("HeaterGain");
				out.print(gson.toJson(gainValue[0].getValue()));
			}
			
			if(commandname.equals("getCurrentVentilationGain"))
			{
				Measurement[] gainValue = (Measurement[])request.getAttribute("VentilationGain");
				out.print(gson.toJson(gainValue[0].getValue()));
			}

			if(commandname.equals("getVentilationGain"))
			{
				Measurement[] gainValue = (Measurement[])request.getAttribute("VentilationGain");
				out.print(gson.toJson(gainValue));
			}
			
			if(commandname.equals("getThermalComfort"))
			{
				// gets temperature measurements
				Measurement[] initialResults = (Measurement[]) request.getAttribute("Measurement");
				
				initialResults = attachHumanComfort(initialResults);
				initialResults = attachPDD(initialResults);

				out.print(gson.toJson(initialResults));
			}
			
			if(commandname.equals("setVentilationGain"))
			{
				String returnString = (String)request.getAttribute("SetGainStatus");
				
				out.print(gson.toJson(returnString));
			}
			
		} catch (Exception ex) {
		}
	}
	
	private Measurement[] attachPDD(Measurement[] initialResults) {
		ThermalComfortGenerator thermalComfortGen = new ThermalComfortGenerator();
		
		for (Measurement measurement : initialResults) {
			double pmv = measurement.getThermalComfort();
			
			measurement.setPDD(thermalComfortGen.calculatePPD(pmv));
		}
		
		return initialResults;
	}

	private Measurement[] attachHumanComfort(Measurement[] initialResults)
	{
		ThermalComfortGenerator thermalComfortGen = new ThermalComfortGenerator();
		
		Double dewPoint = 10.0;
		
		for (Measurement measurement : initialResults) {
			double temperature = measurement.getValue();
			
			measurement.setThermalComfort(thermalComfortGen.calculateThermalComfort(dewPoint, temperature));
			}
		
		return initialResults;
	}
	
}
