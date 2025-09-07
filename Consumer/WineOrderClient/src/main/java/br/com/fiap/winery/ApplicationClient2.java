package br.com.fiap.winery;

import java.net.URL;
import java.util.Scanner;

import javax.xml.namespace.QName;

import jakarta.xml.ws.Service;

public class ApplicationClient2 {
    public static void main(String[] args) {

        try {
            WineStockServiceImplementationService service = new WineStockServiceImplementationService();
            WineStockService port = service.getWineStockServiceImplementationPort();

            System.out.println("Conectando ao serviço de estoque de vinhos...");

            String menu = port.getMenu();
            System.out.println(menu);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Digite o nome do vinho que deseja pedir: ");
            String wineName = scanner.nextLine();

            System.out.print("Digite a quantidade desejada: ");
            int quantity = scanner.nextInt();

            System.out.println("\nEnviando seu pedido...");
            String orderStatus = port.placeOrder(wineName, quantity);

            System.out.println("Resposta do servidor: " + orderStatus);
            scanner.close();

            
            URL url2 = new URL("http://localhost:8086/WineWarningService?wsdl");
            QName qName2 = new QName("http://winery.fiap.com.br/", "WineWarningServiceImplementationService");
            Service service2 = Service.create(url2, qName2);
            WineWarningService wineWarningService = service2.getPort(WineWarningService.class);
            String warn = wineWarningService.sendWarn();
            System.out.println("\n" + warn);


        } catch (Exception e) {
            System.err.println("Erro ao conectar ao serviço. O servidor (Publisher) está rodando?");
            e.printStackTrace();
        }
    }
}
