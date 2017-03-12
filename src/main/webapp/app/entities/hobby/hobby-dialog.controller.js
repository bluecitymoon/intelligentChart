(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('HobbyDialogController', HobbyDialogController);

    HobbyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Hobby'];

    function HobbyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Hobby) {
        var vm = this;

        vm.hobby = entity;
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
            if (vm.hobby.id !== null) {
                Hobby.update(vm.hobby, onSaveSuccess, onSaveError);
            } else {
                Hobby.save(vm.hobby, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:hobbyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
