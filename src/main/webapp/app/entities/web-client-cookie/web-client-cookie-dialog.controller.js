(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WebClientCookieDialogController', WebClientCookieDialogController);

    WebClientCookieDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'WebClientCookie', 'Website', 'ProxyServer'];

    function WebClientCookieDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, WebClientCookie, Website, ProxyServer) {
        var vm = this;

        vm.webClientCookie = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.websites = Website.query();
        vm.proxyservers = ProxyServer.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.webClientCookie.id !== null) {
                WebClientCookie.update(vm.webClientCookie, onSaveSuccess, onSaveError);
            } else {
                WebClientCookie.save(vm.webClientCookie, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:webClientCookieUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.expires = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
