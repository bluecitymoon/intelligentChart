(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('ConnectionLevelDialogController', ConnectionLevelDialogController);

    ConnectionLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ConnectionLevel'];

    function ConnectionLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ConnectionLevel) {
        var vm = this;

        vm.connectionLevel = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.connectionLevel.id !== null) {
                ConnectionLevel.update(vm.connectionLevel, onSaveSuccess, onSaveError);
            } else {
                ConnectionLevel.save(vm.connectionLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:connectionLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
