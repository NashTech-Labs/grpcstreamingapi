package proto.hello

object ClockGrpc {
  val METHOD_GET_TIME: _root_.io.grpc.MethodDescriptor[proto.hello.TimeRequest, proto.hello.TimeResponse] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("proto.Clock", "GetTime"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[proto.hello.TimeRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[proto.hello.TimeResponse])
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("proto.Clock")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(proto.hello.HelloProto.javaDescriptor))
      .addMethod(METHOD_GET_TIME)
      .build()
  
  trait Clock extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = Clock
    def getTime(request: proto.hello.TimeRequest, responseObserver: _root_.io.grpc.stub.StreamObserver[proto.hello.TimeResponse]): Unit
  }
  
  object Clock extends _root_.scalapb.grpc.ServiceCompanion[Clock] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[Clock] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = proto.hello.HelloProto.javaDescriptor.getServices().get(0)
  }
  
  trait ClockBlockingClient {
    def serviceCompanion = Clock
    def getTime(request: proto.hello.TimeRequest): scala.collection.Iterator[proto.hello.TimeResponse]
  }
  
  class ClockBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[ClockBlockingStub](channel, options) with ClockBlockingClient {
    override def getTime(request: proto.hello.TimeRequest): scala.collection.Iterator[proto.hello.TimeResponse] = {
      _root_.scalapb.grpc.ClientCalls.blockingServerStreamingCall(channel, METHOD_GET_TIME, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): ClockBlockingStub = new ClockBlockingStub(channel, options)
  }
  
  class ClockStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[ClockStub](channel, options) with Clock {
    override def getTime(request: proto.hello.TimeRequest, responseObserver: _root_.io.grpc.stub.StreamObserver[proto.hello.TimeResponse]): Unit = {
      _root_.scalapb.grpc.ClientCalls.asyncServerStreamingCall(channel, METHOD_GET_TIME, options, request, responseObserver)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): ClockStub = new ClockStub(channel, options)
  }
  
  def bindService(serviceImpl: Clock, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
    _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
    .addMethod(
      METHOD_GET_TIME,
      _root_.io.grpc.stub.ServerCalls.asyncServerStreamingCall(new _root_.io.grpc.stub.ServerCalls.ServerStreamingMethod[proto.hello.TimeRequest, proto.hello.TimeResponse] {
        override def invoke(request: proto.hello.TimeRequest, observer: _root_.io.grpc.stub.StreamObserver[proto.hello.TimeResponse]): Unit =
          serviceImpl.getTime(request, observer)
      }))
    .build()
  
  def blockingStub(channel: _root_.io.grpc.Channel): ClockBlockingStub = new ClockBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): ClockStub = new ClockStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = proto.hello.HelloProto.javaDescriptor.getServices().get(0)
  
}