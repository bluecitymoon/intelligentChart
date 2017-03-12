(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('HobbyDeleteController',HobbyDeleteController);

    HobbyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Hobby'];

    function HobbyDeleteController($uibModalInstance, entity, Hobby) {
        var vm = this;

        vm.hobby = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Hobby.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
