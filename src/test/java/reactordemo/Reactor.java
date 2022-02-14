package reactordemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author keboom 2022/1/4
 */
public class Reactor implements Runnable {

  final Selector selector;
  final ServerSocketChannel serverSocket;

  public Reactor() throws IOException {
    selector = Selector.open();
    serverSocket = ServerSocketChannel.open();
    serverSocket.bind(new InetSocketAddress(9999));

    //非阻塞
    serverSocket.configureBlocking(false);

    //分步处理,第一步,接收accept事件
    SelectionKey sk =
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
    //attach callback object, Acceptor
    sk.attach(new Acceptor());
  }

  @Override
  public void run() {
    try {
      while (!Thread.interrupted()) {
        selector.select();
        Set selected = selector.selectedKeys();
        Iterator it = selected.iterator();
        while (it.hasNext()) {
          //Reactor负责dispatch收到的事件
          dispatch((SelectionKey) (it.next()));
        }
        selected.clear();
      }
    } catch (IOException ex) { /* ... */ }
  }

  void dispatch(SelectionKey k)
  {
    Runnable r = (Runnable) (k.attachment());
    //调用之前注册的callback对象
    if (r != null)
    {
      r.run();
    }
  }

  class Acceptor implements Runnable {

    @Override
    public void run() {

    }
  }

}
