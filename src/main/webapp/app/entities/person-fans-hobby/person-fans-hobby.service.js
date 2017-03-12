(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('PersonFansHobby', PersonFansHobby);

    PersonFansHobby.$inject = ['$resource'];

    function PersonFansHobby ($resource) {
        var resourceUrl =  'api/person-fans-hobbies/:id';
        var findByPersonUrl =  'api/person-fans-hobbies/person/:id';
        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'loadAllByPersonId' : {
                method: 'GET',
                isArray: true,
                url: findByPersonUrl,
                params: { id : '@id'}
            },
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
