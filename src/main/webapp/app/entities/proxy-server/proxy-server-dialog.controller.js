(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ProxyServerDialogController', ProxyServerDialogController);

    ProxyServerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProxyServer'];

    function ProxyServerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProxyServer) {
        var vm = this;

        vm.proxyServer = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.proxyServer.id !== null) {
                ProxyServer.update(vm.proxyServer, onSaveSuccess, onSaveError);
            } else {
                ProxyServer.save(vm.proxyServer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:proxyServerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.lastValidationDate = false;
        vm.datePickerOpenStatus.lastSuccessDate = false;
        vm.datePickerOpenStatus.lastFailDate = false;
        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
